import { useUser } from "../hooks/useUser";
import { Navigate, useLocation } from "react-router-dom";

function RequireNotAuth({ children }: { children: JSX.Element }) {
  let user = useUser();
  let location = useLocation();

  if (user.id) {
    // Redirect them to the /login page, but save the current location they were
    // trying to go to when they were redirected. This allows us to send them
    // along to that page after they login, which is a nicer user experience
    // than dropping them off on the home page.
    return <Navigate to="/protected/welcome" state={{ from: location }} />;
  }

  return children;
}

export { RequireNotAuth };
