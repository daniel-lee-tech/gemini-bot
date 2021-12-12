import { createTheme } from "@mui/material/styles";

let mainTheme = createTheme({
  palette: {
    mode: "dark",
    // typography: {}
    // primary: {
    //   main: "#006064",
    //   light: "#428e92",
    //   dark: "#00363a",
    // },
    // secondary: {
    //   main: "#bcaaa4",
    //   light: "#efdcd5",
    //   dark: "#8c7b75",
    // },

    background: {
      default: "#222222",
      // paper: grey[900],
    },
  },
});

// mainTheme = createTheme(mainTheme, {
//   palette: {
//     info: {
//       main: mainTheme.palette.secondary.main,
//     },
//     palette: {
//       mode: "dark",
//     },
//   },
// });

export { mainTheme };
