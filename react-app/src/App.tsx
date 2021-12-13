import "./App.css";

import { Outlet } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
import { Navbar } from "./components/Navbar/navbar";
import { Container, CssBaseline, ThemeProvider } from "@mui/material";
import { mainTheme } from "./themes/main-theme";
import { UserAxiosContextProvider } from "./contexts/UserAxiosContext";

function App() {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <UserAxiosContextProvider>
        <ThemeProvider theme={mainTheme}>
          <CssBaseline />
          <Navbar />
          <Container>
            <Outlet />
            <ReactQueryDevtools initialIsOpen={false} />
          </Container>
        </ThemeProvider>
      </UserAxiosContextProvider>
    </QueryClientProvider>
  );
}

export { App };
