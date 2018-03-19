package org.ballerinalang.net.jms.nativeimpl.endpoint.service;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.transport.jms.contract.JMSServerConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;

import java.util.Objects;

/**
 * Get the ID of the connection.
 *
 * @since 0.966
 */
@BallerinaFunction(
        packageName = "ballerina.net.jms",
        functionName = "stop",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "ServiceEndpoint",
                structPackage = "ballerina.net.jms"),
        isPublic = true
)
public class Stop implements NativeCallableUnit {
    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {
        Struct serviceEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);
        Object connectorObject = serviceEndpoint.getNativeData(Constants.SERVER_CONNECTOR);
        try {
            if (Objects.nonNull(connectorObject) && connectorObject instanceof JMSServerConnector) {
                ((JMSServerConnector) connectorObject).stop();
            } else {
                throw new BallerinaException("Cannot stop service. Connection to service endpoint "
                                                     + serviceEndpoint.getName() + " not properly registered.");
            }
        } catch (JMSConnectorException e) {
            throw new BallerinaException(
                    "Error when closing queue/topic listener with service endpoint " + serviceEndpoint.getName(), e);

        }

    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}
