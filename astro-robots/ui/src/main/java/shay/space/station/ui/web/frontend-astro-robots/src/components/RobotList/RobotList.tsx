import './RobotList.css';

function RobotList({ robots, loading }) {
  if (loading) {
    return <div>Loading...</div>;
  }

  if (robots.length === 0) {
    return <div>No robots available.</div>;
  }

  return (
    <ul className="list-group">
      {robots.map((robot, index) => (
        <li key={index} className="list-group-item">
          <h4>{robot.data.name}</h4>
          <p>Call Sign: {robot.data.callSign}</p>
          <p>Model: {robot.data.model}</p>
        </li>
      ))}
    </ul>
  );
}

export default RobotList;
