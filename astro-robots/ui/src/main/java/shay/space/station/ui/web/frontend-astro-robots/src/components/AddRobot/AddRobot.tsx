import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AddRobot.css';

function AddRobot() {
  const [robotName, setRobotName] = useState('');
  const [robotModel, setRobotModel] = useState('');
  const [callsignId, setCallsignId] = useState('');
  const [result, setResult] = useState(null);
  const [specificModels, setSpecificModels] = useState([]);

  useEffect(() => {
    async function fetchSpecificModels() {
      try {
        const response = await axios.get(
          'http://localhost:8080/api/factory/specific-models'
        );
        setSpecificModels(response.data);
      } catch (error) {
        console.error('Error fetching specific models:', error);
      }
    }

    fetchSpecificModels();
  }, []);

  const handleAddRobot = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/robots', {
        name: robotName,
        model: robotModel,
        callSign: callsignId,
      });
      setResult(response.data);
    } catch (error) {
      console.error('Error adding robot:', error);
    }
  };

  return (
    <div>
      <h2>Provision new robot</h2>
      <div className="form-group">
        <label>Name:</label>
        <input
          type="text"
          className="form-control"
          value={robotName}
          onChange={(e) => setRobotName(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label>Model:</label>
        <select
          className="form-control"
          value={robotModel}
          onChange={(e) => setRobotModel(e.target.value)}
        >
          <option value="">Select a model</option>
          {specificModels.map((model, index) => (
            <option key={index} value={model}>
              {model}
            </option>
          ))}
        </select>
      </div>
      <div className="form-group">
        <label>Call Sign:</label>
        <input
          type="text"
          className="form-control"
          value={callsignId}
          onChange={(e) => setCallsignId(e.target.value)}
        />
      </div>
      <button className="btn btn-primary" onClick={handleAddRobot}>
        Add Robot
      </button>
      {result && (
        <div>
          <h4>Result:</h4>
          <pre>{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default AddRobot;
