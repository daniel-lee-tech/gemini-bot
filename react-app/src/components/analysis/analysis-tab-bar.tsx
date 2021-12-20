import * as React from "react";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import {Link} from "react-router-dom";

// function a11yProps(index: number) {
//   return {
//     id: `simple-tab-${index}`,
//     "aria-controls": `simple-tabpanel-${index}`,
//   };
// }

function AnalysisTabBar() {
  const [value, _setValue] = React.useState(0);

  // const handleChange = (newValue: number) => {
  //   setValue(newValue);
  // };

  return (
    <Box sx={{ width: "100%" }}>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs
          value={value}
          // onChange={handleChange}
          aria-label="basic tabs example"
        >
          <Link
            to={"currencyaggregate"}
            style={{
              textDecoration: "none",
              color: "white",
              display: "block",
              paddingRight: 20,
            }}
          >
            <Tab label="Aggregate" />
          </Link>
          <Tab />
          <Tab />
        </Tabs>
      </Box>
    </Box>
  );
}

export { AnalysisTabBar };
