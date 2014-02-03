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
package net.cristcost.study.gwtws.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.websockets.client.WebSocket;
import com.google.gwt.websockets.client.WebSocketCallback;

/**
 * The Class WebSocketApp.
 */
public class WebSocketApp extends Composite implements WebSocketCallback {

  /**
   * The Interface WebSocketAppUiBinder.
   */
  interface WebSocketAppUiBinder extends UiBinder<Widget, WebSocketApp> {
  }

  /** The ui binder. */
  private static WebSocketAppUiBinder uiBinder = GWT.create(WebSocketAppUiBinder.class);

  @UiField
  FlowPanel messagesPanel;

  @UiField
  TextBox textInputTextbox;

  private WebSocket ws;

  /**
   * Instantiates a new web socket app.
   */
  public WebSocketApp() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void onConnect() {
    String wsurl = "TODO!";
    appendClientMessage("Connected to server via WebSocket to " + wsurl);
  }

  @Override
  public void onDisconnect() {
    ws = null;
    appendServerMessage("Disconnected from server");
  }

  @Override
  public void onMessage(String message) {
    if (message != null) {
      appendServerMessage(message);
    }
  }

  @UiHandler("connectButton")
  void handleConnectButtonClick(ClickEvent e) {

    Dictionary dictionary = Dictionary.getDictionary("config");

    if (dictionary.get("wsurl") == null) {
      appendErrorMessage("No WebSocket url specified!");
    }

    ws = new WebSocket(this);
    ws.connect(dictionary.get("wsurl"));

    appendClientMessage("Connect");
  }

  @UiHandler("disconnectButton")
  void handleDisconnectButtonClick(ClickEvent e) {
    if (ws != null) {
      appendClientMessage("Disconnecting");
      ws.close();
    } else {
      appendErrorMessage("Not connected, can't disconnect");
    }
  }

  @UiHandler("sendButton")
  void handleSendButtonClick(ClickEvent e) {

    String message = textInputTextbox.getValue();
    if (ws != null) {
      ws.send(message);
    } else {
      appendErrorMessage("Not connected, cannot send: " + message);
    }
  }

  private void appendClientMessage(String text) {
    appendMessage(text, "text-warning");
  }

  private void appendErrorMessage(String text) {
    appendMessage(text, "text-danger");
  }

  private void appendMessage(String text, String style) {
    Label label = new Label(text);
    label.setStyleName(style);
    messagesPanel.add(label);
  }

  private void appendServerMessage(String text) {
    appendMessage(text, "text-primary");
  }
}
