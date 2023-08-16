// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// function MainScreen() {
//   const [commands, setCommands] = useState([]);

//   useEffect(() => {
//     async function fetchCommands() {
//       try {
//         const response = await axios.get('http://localhost:8080/api/commands');
//         setCommands(response.data);
//       } catch (error) {
//         console.error('Error fetching commands:', error);
//       }
//     }

//     fetchCommands();
//   }, []);

//   return (
//     <div className="main-screen">
//       <h2>Robot Management System</h2>
//       <div className="command-list">
//         <h3>Available Commands:</h3>
//         <ul>
//           {commands.map((command, index) => (
//             <li key={index}>{command}</li>
//           ))}
//         </ul>
//       </div>
//     </div>
//   );
// }

// export default MainScreen;

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function MainScreen() {
  const [commands, setCommands] = useState([]);

  useEffect(() => {
    async function fetchCommands() {
      try {
        const response = await axios.get('http://localhost:8080/api/commands');
        setCommands(response.data);
      } catch (error) {
        console.error('Error fetching commands:', error);
      }
    }

    fetchCommands();
  }, []);

  return (
    <div className="main-screen">
      <h2>Robot Management System</h2>
      <div className="command-list">
        <h3>Available Commands:</h3>
        <ul className="list-group">
          {commands.map((command, index) => (
            <li className="list-group-item" key={index}>
              <Link
                className="command-link"
                to={`/${command.replace(/\s+/g, '-').toLowerCase()}`}
              >
                {command}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default MainScreen;
