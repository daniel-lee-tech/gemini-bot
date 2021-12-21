import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import {Link, useLocation} from "react-router-dom";
import {useCallback, useMemo, useState} from "react";

// function a11yProps(index: number) {
//   return {
//     id: `simple-tab-${index}`,
//     "aria-controls": `simple-tabpanel-${index}`,
//   };
// }

function AnalysisTabBar() {
  const { pathname } = useLocation();

  const [tabValue, setTabValue] = useState(0);

  const setTabsByLocation = useCallback(() => {
    if (pathname.includes("transfers")) {
      setTabValue(2);
    } else if (pathname.includes("fees")) {
      setTabValue(1);
    } else {
      setTabValue(0);
    }
  }, [pathname]);

  useMemo(() => {
    setTabsByLocation();
  }, [setTabsByLocation]);

  return (
    <Box sx={{ width: "100%" }}>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs value={tabValue} aria-label="basic tabs example">
          <Link
            to={"currencyaggregate"}
            style={{
              textDecoration: "none",
              color: "white",
              display: "block",
              paddingRight: 20,
            }}
          >
            <Tab value={0} label="Aggregate" />
          </Link>
          <Link
            to={"fees"}
            style={{
              textDecoration: "none",
              color: "white",
              display: "block",
              paddingRight: 20,
            }}
          >
            <Tab value={1} label="Fees" />
          </Link>
          <Link
            to={"transfers"}
            style={{
              textDecoration: "none",
              color: "white",
              display: "block",
              paddingRight: 20,
            }}
          >
            <Tab value={2} label="Transfers" />
          </Link>
          <Tab />
        </Tabs>
      </Box>
    </Box>
  );
}

export { AnalysisTabBar };
