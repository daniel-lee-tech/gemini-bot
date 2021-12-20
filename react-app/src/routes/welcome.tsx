import { Box, Button, Typography } from "@mui/material";
import { Link } from "react-router-dom";

function Welcome() {
  return (
    <Box>
      <Typography variant={"h2"} sx={{ marginY: 3 }}>
        Welcome
      </Typography>
      <Typography variant={"h3"} gutterBottom={true}>
        Getting Started:
      </Typography>
      <Typography variant={"h5"} sx={{ marginY: 5 }}>
        Step 1:
      </Typography>
      <Typography fontSize={19} marginY={3}>
        Retrieve your API keys from Gemini. Instructions on how to do this is
        located here:
      </Typography>
      <Button
        variant={"outlined"}
        href="https://support.gemini.com/hc/en-us/articles/360031080191-How-do-I-create-an-API-key-"
        target="_blank"
        rel="noreferrer"
      >
        Creating your Gemini API keys
      </Button>

      <Typography variant={"h5"} sx={{ marginY: 5 }} gutterBottom={true}>
        Step 2:
      </Typography>
      <Typography fontSize={19} marginY={3}>
        Add your API keys here.
      </Typography>
      <Link
        style={{ textDecoration: "none" }}
        to="/protected/apikeys"
        rel="noreferrer"
      >
        <Button variant={"outlined"}>API Keys page</Button>
      </Link>

      <Typography variant={"h5"} sx={{ marginY: 5 }} gutterBottom={true}>
        Step 3:
      </Typography>
      <Typography fontSize={19} marginY={3}>
        Import your transfers and trades
      </Typography>
      <Link
        style={{ textDecoration: "none", display: "block", marginBottom: 24 }}
        to="/protected/transfers"
        rel="noreferrer"
      >
        <Button variant={"outlined"}>Import transfers here</Button>
      </Link>

      <Link
        style={{ textDecoration: "none" }}
        to="/protected/trades"
        rel="noreferrer"
      >
        <Button variant={"outlined"}>Import trades here</Button>
      </Link>
    </Box>
  );
}

export { Welcome };
