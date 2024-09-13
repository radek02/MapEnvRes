import 'bootstrap/dist/css/bootstrap.min.css';
import '../index.css'
import { Navbar, Nav } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';

function UserHeader() {
  return (
    <Navbar bg="darkblue" variant="dark" expand="lg" fixed="top">
        <Navbar.Brand as={Link} to="/home" className="navbar-brand-custom">
            Environment reservation for MEP
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav" className="justify-content-end">
            <Nav>
                <Nav.Link as={Link} to="/addUserGroup" className="my-2 mx-3">Add user group</Nav.Link>
                <Nav.Link as={Link} to="/" className="my-2 mx-3">Logout</Nav.Link>
            </Nav>
        </Navbar.Collapse>
    </Navbar>
  );
}

export default UserHeader;