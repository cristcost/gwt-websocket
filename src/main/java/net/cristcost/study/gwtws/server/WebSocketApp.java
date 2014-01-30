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

import java.io.IOException;

/**
 * The Class WebSocketApp.
 */
public class WebSocketApp implements WebSocket.OnTextMessage {

  private Connection connection;
  private SampleWebSocketServlet sampleWebSocketServlet;

  public WebSocketApp(SampleWebSocketServlet sampleWebSocketServlet) {
    this.sampleWebSocketServlet = sampleWebSocketServlet;
  }

  @Override
  public void onOpen(Connection newConnection) {
    sampleWebSocketServlet.add(this);
    connection = newConnection;
    try {
      connection.sendMessage("Server received Web Socket upgrade and added it to Receiver List.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onClose(int closeCode, String message) {
    sampleWebSocketServlet.remove(this);
  }

  @Override
  public void onMessage(String data) {
    System.out.println("Received: " + data);
  }

  public void sendMessage(String data) throws IOException {
    connection.sendMessage(data);
  }

  public boolean isOpen() {
    return connection.isOpen();
  }

}
