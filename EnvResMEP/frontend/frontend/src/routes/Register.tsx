
// import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from 'react-bootstrap';
import GuestHeader from '../components/GuestHeader';
import RegisterForm from '../components/RegisterForm';

function Resgister() {
    return (
        <Container className="bg-light p-4 rounded-4">
          <GuestHeader />
          <RegisterForm />
    
        </Container>
      );
}

export default Resgister;