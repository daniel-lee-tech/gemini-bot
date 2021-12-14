import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { App } from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Login } from "./routes/login";
import { Register } from "./routes/register";
import { RequireAuth } from "./security/require-auth";
import { Transfers } from "./routes/transfers";
import { RequireNotAuth } from "./security/require-not-auth";
import { ApiKeys } from "./routes/api-keys";

function ProtectedRoute({
  path,
  element,
}: {
  path: string;
  element: JSX.Element;
}): JSX.Element {
  return <Route path={path} element={<RequireAuth>{element}</RequireAuth>} />;
}

function OnlyLoggedOut({
  path,
  element,
}: {
  path: string;
  element: JSX.Element;
}): JSX.Element {
  return (
    <Route path={path} element={<RequireNotAuth>{element}</RequireNotAuth>} />
  );
}

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          {OnlyLoggedOut({ path: "login", element: <Login /> })}
          {OnlyLoggedOut({ path: "register", element: <Register /> })}
          <Route path="protected/">
            {ProtectedRoute({ path: "transfers", element: <Transfers /> })}
            {ProtectedRoute({ path: "apikeys", element: <ApiKeys /> })}
            {ProtectedRoute({ path: "welcome", element: <h1>Welcome</h1> })}
          </Route>
          <Route path="*" element={<h1>There's nothing here!</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
