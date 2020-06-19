package gui;

/**
 * Класс состояния игры: координаты цели, координаты робота и напраление робота.
 */

class GameStatement {
    private final double robotPositionX;
    private final double robotPositionY;
    private final double robotDirection;
    private final int targetPositionX;
    private final int targetPositionY;

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