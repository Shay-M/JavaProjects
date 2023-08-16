import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import MainScreen from './components/MainScreen';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import ViewRobots from './components/ViewRobots/ViewRobots';
import AddRobot from './components/AddRobot/AddRobot';
import SelectRobot from './components/issue-commands/SelectRobot';

function App() {
  return (
    <Router>
      <div className="App">
        <header className="header">
          <h1>Space Station Astro Robots</h1>
        </header>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
          <div className="container">
            <Routes>
              <Route path="/" element={<MainScreen />} />
              <Route
                path="/list-fleet-robots"
                element={<ViewRobots showDetails={true} />}
              />
              <Route path="/issue-commands-to-robot" element={<SelectRobot />} />
              <Route path="/provision-new-robot" element={<AddRobot />} />
            </Routes>
          </div>
        </nav>

        <div className="container content"></div>
      </div>
    </Router>
  );
}

export default App;
