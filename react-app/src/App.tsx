import "./App.css";

import { Link, Outlet } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
import { AxiosConfigContextProvider } from "./contexts/AxiosContext";

function App() {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <AxiosConfigContextProvider>
        <div>
          <Link to="/register">Register</Link> | <Link to="/login">Login</Link>
          <Outlet />
          <ReactQueryDevtools initialIsOpen={false} />
        </div>
      </AxiosConfigContextProvider>
    </QueryClientProvider>
  );
}

export { App };
