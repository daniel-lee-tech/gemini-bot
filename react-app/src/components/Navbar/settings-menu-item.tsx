import Typography from "@mui/material/Typography";
import MenuItem from "@mui/material/MenuItem";

function SettingsMenuItem({
  setting,
  handleCloseNavMenu,
  smallerThanSmScreen,
}: {
  setting: string;
  handleCloseNavMenu: () => void;
  smallerThanSmScreen: boolean;
}) {
  return (
    <MenuItem key={setting} onClick={handleCloseNavMenu}>
      <Typography
        variant={smallerThanSmScreen ? "h6" : "h5"}
        gutterBottom={true}
        textAlign="center"
      >
        {setting}
      </Typography>
    </MenuItem>
  );
}

export { SettingsMenuItem };
