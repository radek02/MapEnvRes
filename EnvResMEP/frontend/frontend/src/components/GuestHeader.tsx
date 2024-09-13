import 'bootstrap/dist/css/bootstrap.min.css';
import '../index.css'
import { Navbar, Nav } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';

function GuestHeader() {
  return (
    <Navbar bg="darkblue" variant="dark" expand="lg" fixed="top">
        <Navbar.Brand as={Link} to="/" className="navbar-brand-custom">
            Environment reservation for MEP
        </Navbar.Brand>
    </Navbar>
  );
}

export default GuestHeader;