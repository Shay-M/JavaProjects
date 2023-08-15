// package shay.space.station.infra.base;
//
//
// import shay.space.station.tools.Tool;
//
// import java.util.List;
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReadWriteLock;
// import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//
// public abstract class AbstractRobot implements Robot, RobotData {
//     private static final double SuccessPres = 0.9;
//
//     private final ReadWriteLock lock = new ReentrantReadWriteLock(); // like ReentrantLock
//     private final Lock m_readLock = lock.readLock();
//     private final Lock m_writeLock = lock.writeLock();
//
//     protected StateRobot m_statesRobot;
//     protected RobotData m_robotData;
//     protected final List<Tool> m_tools;
//
//     protected AbstractRobot(final List<Tool> tools) {
//         m_tools = tools;
//
//         if (ProbabilityOfSuccess.isSuccess(SuccessPres)) {
//             setRobotStates(new ActiveState(this));
//         }
//         else {
//             // setStatesRobot(new FailingState());
//         }
//
//     }
//
//
//     @Override
//     public RobotData getData() {
//         return m_robotData;
//     }
//
//     @Override
//     public StateRobot getRobotStates() {
//         m_readLock.lock();
//         try {
//             return m_statesRobot;
//         }
//         finally {
//             m_readLock.unlock();
//         }
//     }
//
//     @Override
//     public void setRobotStates(StateRobot statesRobot) {
//         m_writeLock.lock();
//         try {
//             m_statesRobot = statesRobot;
//         }
//         finally {
//             m_writeLock.unlock();
//         }
//     }
//
//     @Override
//
//     public void builder(final String name, final String callSign) {
//         m_robotData = new RobotData(name, callSign, this.getClass().getSimpleName(), m_tools);
//     }
//
//     @Override
//     public String toString() {
//         return m_robotData.toString() + " Robot State: " + "m_statesRobot.toString()";
//     }
// }
//
