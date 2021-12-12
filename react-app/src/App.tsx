import "./App.css";

import { Outlet } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
import { AxiosConfigContextProvider } from "./contexts/AxiosContext";
import { Navbar } from "./components/Navbar/navbar";
import { Container, CssBaseline, ThemeProvider } from "@mui/material";
import { mainTheme } from "./themes/main-theme";

function App() {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <AxiosConfigContextProvider>
        <ThemeProvider theme={mainTheme}>
          <CssBaseline />
          <Navbar />

          <Container>
            <Outlet />
            <ReactQueryDevtools initialIsOpen={false} />
          </Container>
        </ThemeProvider>
      </AxiosConfigContextProvider>
    </QueryClientProvider>
  );
}

export { App };
