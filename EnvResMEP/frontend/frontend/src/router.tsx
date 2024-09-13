import { createBrowserRouter } from "react-router-dom";

import App from "./App.tsx";
import AddUserGroup from "./routes/AddUserGroup.tsx";
import Home from "./routes/Home.tsx";
import Register from "./routes/Register.tsx";
import ReservationsForEnvironment from "./routes/ReservationsForEnvironment.tsx";
import ReservationsForCluster from "./routes/ReservationsForCluster.tsx";

export const router = createBrowserRouter([
    { path: "/", element: <App /> },
    { path: "/register", element: <Register /> },
    { path: "/home", element: <Home /> },
    { path: "/addUserGroup", element: <AddUserGroup />},
    { path: "/reservations/byEnvironmentId/:environmentId", element: <ReservationsForEnvironment /> },
    { path: "/reservations/byClusterId/:clusterId", element: <ReservationsForCluster /> }
]);