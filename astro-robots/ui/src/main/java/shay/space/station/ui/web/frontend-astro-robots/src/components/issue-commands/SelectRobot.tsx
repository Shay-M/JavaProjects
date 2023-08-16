import React, { useState, useEffect } from 'react';
import axios from 'axios';

function SelectRobot() {
  const [robots, setRobots] = useState([]);
  const [selectedRobot, setSelectedRobot] = useState(null);

  useEffect(() => {
    fetchRobots();
  }, []);

  const fetchRobots = async () => {
    try {
      const response = await axios.get('/api/robots');
      setRobots(response.data);
    } catch (error) {
      console.error('Error fetching robots:', error);
    }
  };

  const handleRobotSelect = (robot) => {
    setSelectedRobot(robot);
  };

  const renderCommands = () => {
    if (!selectedRobot) {
      return <p>Select a robot to issue commands.</p>;
    }

    return (
      <div>
        <h4>Selected Robot:</h4>
        <pre>{JSON.stringify(selectedRobot, null, 2)}</pre>
        <h4>Available Commands:</h4>
        {/* Implement command buttons here */}
      </div>
    );
  };

  return (
    <div>
      <h2>Issue commands to robot</h2>
      <div>
        {robots.map((robot) => (
          <div key={robot.data.callSign}>
            <button className="btn btn-link" onClick={() => handleRobotSelect(robot)}>
              {robot.data.callSign}
            </button>
          </div>
        ))}
      </div>
      {renderCommands()}
    </div>
  );
}

export default SelectRobot;
