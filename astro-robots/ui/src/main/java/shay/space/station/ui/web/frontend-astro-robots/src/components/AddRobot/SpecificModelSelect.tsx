import React from 'react';

function SpecificModelSelect({ value, onChange, models }) {
  return (
    <select className="form-control" value={value} onChange={onChange}>
      <option value="">Select a model</option>
      {models.map((model, index) => (
        <option key={index} value={model}>
          {model}
        </option>
      ))}
    </select>
  );
}

export default SpecificModelSelect;
