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
import { CurrencyAnalysis } from "./routes/analysis/currency-analysis";
import { TransfersAnalysis } from "./routes/analysis/transfers-analysis";
import { FeesAnalysis } from "./routes/analysis/fees-analysis";

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
                    <Route path="" element={<CurrencyAnalysis />} />
                    <Route
                      path="currencyaggregate"
                      element={<CurrencyAnalysis />}
                    />
                    <Route path="fees" element={<FeesAnalysis />} />
                    <Route path="transfers" element={<TransfersAnalysis />} />
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
