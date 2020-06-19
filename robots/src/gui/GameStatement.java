package gui;


class GameStatement {
    private double robotPositionX;
    private double robotPositionY;
    private double robotDirection;
    private int targetPositionX;
    private int targetPositionY;

    GameStatement(double robotPositionX, double robotPositionY, double robotDirection, int targetPositionX, int targetPositionY) {
        this.robotPositionX = robotPositionX;
        this.robotPositionY = robotPositionY;
        this.robotDirection = robotDirection;
        this.targetPositionX = targetPositionX;
        this.targetPositionY = targetPositionY;
    }

    double getRobotPositionX() {
        return robotPositionX;
    }

    double getRobotPositionY() {
        return robotPositionY;
    }

    double getRobotDirection() {
        return robotDirection;
    }

    int getTargetPositionX() {
        return targetPositionX;
    }

    int getTargetPositionY() {
        return targetPositionY;
    }
}
