package tjcore.api.axle;

import tjcore.common.pipelike.rotation.AxleWhole;

public interface IRotationProvider {
    void pushRotation(float rotationSpeed, float torque);
    void joinNet(boolean recalculate);
    float getRotation();
    void setAxleWhole(AxleWhole axle);
}
