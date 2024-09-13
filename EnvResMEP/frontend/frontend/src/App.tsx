
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import GuestHeader from './components/GuestHeader';
import LoginForm from './components/LoginForm';


function App() {
  return (
    <>
      <GuestHeader />
      <Container className="bg-light p-4 rounded-4">
        <LoginForm />
      
        <div className="text-start mt-1">
          <Link to="/register">Register</Link>
        </div>

      </Container>
    </>
  );
}

export default App
