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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class SampleWebSocketServlet.
 */
@SuppressWarnings("serial")
public class SampleWebSocketServlet extends WebSocketServlet {

  private final Set<WebSocketApp> members = new CopyOnWriteArraySet<WebSocketApp>();
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    getServletContext().getNamedDispatcher("default").forward(request, response);
  }

  @Override
  public void init() throws ServletException {
    super.init();
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        System.out.println("Running Server Message Sending");
        for (WebSocketApp member : members) {
          System.out.println("Trying to send to Member!");
          if (member.isOpen()) {
            System.out.println("Sending!");
            try {
              member.sendMessage("Sending a Message to you Guys! " + new Date() + "\n");
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
    return new WebSocketApp(this);
  }

  public void remove(WebSocketApp webSocketApp) {
    members.remove(webSocketApp);
  }

  public void add(WebSocketApp webSocketApp) {
    members.add(webSocketApp);
  }
}
