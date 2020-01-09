package com.chernov.signalrtest;

import android.util.Log;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

public class SignalRListener {
    private static SignalRListener instance;

    HubConnection hubConnection;

    public SignalRListener() {
        hubConnection = HubConnectionBuilder
                .create("http://m.fstage.xyz/api/pubcomet/comet?context=finom")
//                .create("http://beta.finom.co/api/pubcomet/comet?context=finom")
                .withHeader("Cookie", ".token=de6eac6c-e44f-4dac-a073-da04a7d600ff; .language=en; project-x=altadev")
                .shouldSkipNegotiate(true)
                .build();
    }

    public static SignalRListener getInstance() {
        if (instance == null) {
            instance = new SignalRListener();
        }
        return instance;
    }

    public boolean startConnection() {
        if (hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
            hubConnection.on("type", () -> Log.d("SignalR msg", "new msg"));
            hubConnection.onClosed(exception -> {
                        Log.d("SignalR", "Connection closed");
                        reconnect();
                    }
            );
            reconnect();
            return true;
        } else {
            return false;
        }
    }

    private void reconnect() {
        hubConnection.setServerTimeout(30000);
        hubConnection.start().blockingAwait();
    }

    public boolean stopConnection() {
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.stop();
            return true;
        } else {
            return false;
        }
    }
}
