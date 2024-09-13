// START GENAI@CHATGPT4
import {SyntheticEvent, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from "react-router-dom";
import "../index.css";


function RegisterForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (event: SyntheticEvent) => {
    event.preventDefault();
    // TODO - perform register operation
    console.log(`REGISTER Username: ${username}, Password: ${password}`);
    navigate("/home");
  };

  return (
    <form onSubmit={handleSubmit} className="text-start">
      <div className="flexRowContainer mb-3">
        <label className="form-label me-2 fw-bold">Username:</label>
        <input type="text" className="form-control w-100" value={username} onChange={e => setUsername(e.target.value)} required />
      </div>
      <div className="flexRowContainer mb-3">
        <label className="form-label me-2 fw-bold">Password:</label>
        <input type="password" className="form-control w-100" value={password} onChange={e => setPassword(e.target.value)} required />
      </div>
      <div className="flexRowContainer mb-3">
        <label className="form-label me-2 fw-bold" style={{whiteSpace: 'nowrap'}}>Repeat password:</label>
        <input type="password" className="form-control w-100" value={repeatPassword} onChange={e => setRepeatPassword(e.target.value)} required />
      </div>
      <button type="submit" className="btn btn-primary mt-2">Register</button>
    </form>
  );
}

export default RegisterForm;
// END GENAI@CHATGPT4