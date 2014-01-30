/*
 * Copyright 2013, Cristiano Costantini, Giuseppe Gerla, Michele Ficarra, Sergio Ciampi, Stefano
 * Cigheri.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package net.cristcost.study.gwtws.server;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class SampleWebSocketServlet.
 */
@SuppressWarnings("serial")
public class SampleWebSocketServlet extends WebSocketServlet {

  private static Logger logger = Logger.getLogger(SampleWebSocketServlet.class.getName());

  private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  private String lastData = null;

  private final Set<WebSocketHandler> members = new CopyOnWriteArraySet<WebSocketHandler>();

  public void add(WebSocketHandler webSocketHandler) {
    logger.log(Level.INFO, "Adding client");
    members.add(webSocketHandler);
  }

  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
    return new WebSocketHandler(this);
  }

  @Override
  public void init() throws ServletException {
    super.init();
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        for (final WebSocketHandler member : members) {
          if (member.isOpen()) {
            if (lastData != null) {
              member.sendMessage("The last data was: " + lastData + " [Server time" + new Date()
                  + "]");
            } else {
              member.sendMessage("No data received yet [Server time" + new Date() + "]");
            }
          }
        }
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  public void remove(WebSocketHandler webSocketHandler) {
    logger.log(Level.INFO, "Removing client");
    members.remove(webSocketHandler);
  }

  public void setLastData(String data) {
    lastData = data;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    getServletContext().getNamedDispatcher("default").forward(request, response);
  }
}
