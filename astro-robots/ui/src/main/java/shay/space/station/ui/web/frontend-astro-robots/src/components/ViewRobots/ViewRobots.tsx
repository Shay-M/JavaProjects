import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import Axios

// const showDetails: boolean = true;
function ViewRobots({ showDetails }) {
  const [robots, setRobots] = useState([]);

  useEffect(() => {
    fetchRobots();
  }, []);

  const fetchRobots = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/robots');
      setRobots(response.data);
    } catch (error) {
      console.error('Error fetching robots:', error);
    }
  };

  if (robots.length === 0) {
    return <div>No robots available.</div>;
  }

  // return (
  //   <div className="container">
  //     <h2>Robot List</h2>
  //     <table className="table">
  //       <thead>
  //         <tr>
  //           <th>Name</th>
  //           <th>Call Sign</th>
  //           <th>Model</th>
  //         </tr>
  //       </thead>
  //       <tbody>
  //         {robots.map((robot) => (
  //           <tr key={robot.data.callSign}>
  //             <td>{robot.data.name}</td>
  //             <td>{robot.data.callSign}</td>
  //             <td>{robot.data.model}</td>
  //           </tr>
  //         ))}
  //       </tbody>
  //     </table>
  //   </div>
  // );

  return (
    <div className="container">
      <h2>Robot List</h2>
      <table className="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Call Sign</th>
            {showDetails && <th>Model</th>}
            {showDetails && <th>States</th>}
            {showDetails && <th>Tools</th>}
          </tr>
        </thead>
        <tbody>
          {robots.map((robot) => (
            <tr key={robot.data.callSign}>
              <td>{robot.data.name}</td>
              <td>{robot.data.callSign}</td>
              {showDetails && <td>{robot.data.model}</td>}
              {showDetails && <td>{robot.states.stateName}</td>}
              {showDetails && (
                <td>
                  {robot.tools.map((tool) => (
                    <div key={tool.toolName}>
                      {tool.toolName} - {tool.toolState}
                    </div>
                  ))}
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ViewRobots;
