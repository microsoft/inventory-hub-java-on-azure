package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.EventHubOutput;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import com.microsoft.azure.serverless.functions.annotation.TimerTrigger;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

public class POS {
    @FunctionName("Point-of-Sale")
    public String functionHandler(@TimerTrigger(name = "timerInfo", schedule = "*/5 * * * * *") 
                                      String timerInfo,
                                  @EventHubOutput(name = "data", eventHubName = "inventoryeh",
                                      connection = "InventoryEventHubConnectionString")
                                  String Output,
                                  final ExecutionContext executionContext) {
        executionContext.getLogger().info("Timer trigger input: " + timerInfo);
        final Gson gson = new GsonBuilder().create();
        final PayloadEvent payload = new PayloadEvent(10);
        byte[] payloadBytes = gson.toJson(payload).getBytes(Charset.defaultCharset());

        Output = gson.toJson(payload);

        return Output;
    }

    /**
     * actual application-payload, ex: a telemetry event
     */
    static final class PayloadEvent {
        PayloadEvent(final int seed) {
            this.id = "EventHubTimeTrigger" + new Random().nextInt(seed);
            this.currentTime = new Date().toString();
            this.longProperty = seed * new Random().nextInt(seed);
            this.intProperty = seed * new Random().nextInt(seed);
        }

        public String id;
        public String currentTime;
        public long longProperty;
        public int intProperty;
    }
}