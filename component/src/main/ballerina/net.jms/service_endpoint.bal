package ballerina.net.jms;

import ballerina/io;

public struct ConsumerEndpoint {
    Context context;
    ServiceEndpointConfiguration config;
}

public struct ServiceEndpointConfiguration {
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryName;
    string destinationType;
    boolean clientCaching;
    string connectionUsername;
    string connectionPassword;
    string configFilePath;
    int connectionCount;
    int sessionCount;
    map properties;
}

public function <ServiceEndpointConfiguration config> ServiceEndpointConfiguration() {
    config.connectionFactoryName = "ConnectionFactory";
    config.destinationType = "queue";
    config.clientCaching = true;
    config.connectionCount = 5;
    config.sessionCount = 10;
}

public function <ConsumerEndpoint ep> init (ServiceEndpointConfiguration config) {
    ep.config = config;
    ep.initEndpoint();
}

public native function<ConsumerEndpoint ep> initEndpoint ();

public native function <ConsumerEndpoint ep> register (typedesc serviceType);

public native function <ConsumerEndpoint ep> start ();

public native function <ConsumerEndpoint ep> getClient () returns (Context);

public native function <ConsumerEndpoint ep> stop ();
