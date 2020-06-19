package gui;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Модель робота (код получения координат и моделирования движения)
 * обозреваемый объект (Observable),
 * чтобы затем окно могло подписаться на уведомления от модели и
 * обновлять свое состояние
 */
class GameModel implements Model {
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;


    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    void setTargetPosition(Point p) {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    void onModelUpdateEvent() {
        double distance = distance(targetPositionX, targetPositionY,
                robotPositionX, robotPositionY);
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angleBetweenRobotAnTarget = asNormalizedRadians(angleToTarget - robotDirection);
        double angularVelocity = 0;

        if (angleBetweenRobotAnTarget < Math.PI) {
            angularVelocity = maxAngularVelocity;
        } else {
            angularVelocity = -maxAngularVelocity;
        }
        if (Math.abs(angleBetweenRobotAnTarget) < 0.1) {
            velocity = maxVelocity;
        } else {
            velocity = distance * Math.abs(angularVelocity) / 2;
        }
        moveRobot(velocity, angularVelocity, 10);
    }


    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }
        robotPositionX = newX;
        robotPositionY = newY;
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
        notifyAllObservers();
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers)
            observer.onUpdate();
    }

    @Override
    public GameStatement getState() {
        return new GameStatement(robotPositionX, robotPositionY, robotDirection, targetPositionX, targetPositionY);
    }

}