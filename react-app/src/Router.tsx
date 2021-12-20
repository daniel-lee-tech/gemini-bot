import { Navbar } from "./components/Navbar/navbar";
import { Container } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Login } from "./routes/login";
import { Register } from "./routes/register";
import { Transfers } from "./routes/transfers";
import { Analysis } from "./routes/analysis";
import { Trades } from "./routes/trades";
import { ApiKeys } from "./routes/api-keys";
import { useAppSelector } from "./redux/hooks/redux";
import { selectUser } from "./redux/slices/userSlice";
import { App } from "./App";
import { Welcome } from "./routes/welcome";
import { CurrencyAggregate } from "./routes/analysis/currency-aggregate";

function Router() {
  const { id } = useAppSelector(selectUser);

  return (
    <BrowserRouter>
      <Navbar />
      <Container>
        <Routes>
          <Route path="/" element={<App />}>
            {!Number(id) && (
              <>
                <Route path="login" element={<Login />} />
                <Route path="register" element={<Register />} />
              </>
            )}

            <Route path="protected/">
              {Number(id) ? (
                <>
                  <Route path="transfers" element={<Transfers />} />
                  <Route path="analysis/" element={<Analysis />}>
                    <Route path="" element={<CurrencyAggregate />} />
                    <Route
                      path="currencyaggregate"
                      element={<CurrencyAggregate />}
                    />
                  </Route>
                  <Route path="trades" element={<Trades />} />
                  <Route path="apikeys" element={<ApiKeys />} />
                  <Route path="welcome" element={<Welcome />} />
                </>
              ) : (
                <Route path="*" element={<h1>Please log in</h1>} />
              )}
            </Route>
            <Route path="*" element={<h1>There's nothing here!</h1>} />
          </Route>
        </Routes>
      </Container>
    </BrowserRouter>
  );
}

export { Router };
