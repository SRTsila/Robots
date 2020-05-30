package gui;

/*
Класс состояния робота и цели - их координаты и напраление робота.
 */
class GameStatement {
    volatile double m_robotPositionX;
    volatile double m_robotPositionY;
    volatile double m_robotDirection;
    volatile int m_targetPositionX;
    volatile int m_targetPositionY;

    GameStatement(double m_robotPositionX, double m_robotPositionY, double m_robotDirection, int m_targetPositionX, int m_targetPositionY) {
        this.m_robotPositionX = m_robotPositionX;
        this.m_robotPositionY = m_robotPositionY;
        this.m_robotDirection = m_robotDirection;
        this.m_targetPositionX = m_targetPositionX;
        this.m_targetPositionY = m_targetPositionY;
    }
}
