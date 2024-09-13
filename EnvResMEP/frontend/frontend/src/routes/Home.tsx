import {environments, clusters} from "../sampleData.json"; 
import "../index.css";
import UserHeader from "../components/UserHeader";
import { Link, useNavigate } from "react-router-dom";

function Home() {
    const navigate = useNavigate();
    
    return (
        <div>
            <UserHeader />
            <div className="m-5 p-2">
                {clusters.map((cluster) => (
                    <div key={cluster.id} className="cluster" onClick={() => navigate(`/reservations/byClusterId/${cluster.id}`)}>
                        <h3>{cluster.name}</h3>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Home;