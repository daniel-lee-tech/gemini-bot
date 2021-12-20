import { Outlet } from "react-router-dom";
import { AnalysisTabBar } from "../components/analysis/analysis-tab-bar";

function Analysis() {
  return (
    <div>
      <AnalysisTabBar />

      <Outlet />
    </div>
  );
}

export { Analysis };
