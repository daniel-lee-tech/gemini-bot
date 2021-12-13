import Box from "@mui/material/Box";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import { Settings } from "@mui/icons-material";
import Menu from "@mui/material/Menu";
import { SettingsMenuItem } from "./settings-menu-item";
import React from "react";

interface SettingsMenuProps {
  handleOpenUserMenu: (event: React.MouseEvent<HTMLElement>) => void;
  anchorElUser: HTMLElement | null;
  handleCloseUserMenu: () => void;
  settings: string[];
  handleCloseNavMenu: () => void;
  smallerThanSmScreen: boolean;
}

function SettingsMenu({
  handleOpenUserMenu,
  anchorElUser,
  handleCloseUserMenu,
  settings,
  handleCloseNavMenu,
  smallerThanSmScreen,
}: SettingsMenuProps) {
  return (
    <Box sx={{ flexGrow: 0 }}>
      <Tooltip title="Open settings">
        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
          <Settings />
        </IconButton>
      </Tooltip>
      <Menu
        sx={{ mt: "45px" }}
        id="menu-appbar"
        anchorEl={anchorElUser}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        open={Boolean(anchorElUser)}
        onClose={handleCloseUserMenu}
      >
        {settings.map((setting) => (
          <SettingsMenuItem
            key={setting}
            setting={setting}
            handleCloseNavMenu={handleCloseNavMenu}
            smallerThanSmScreen={smallerThanSmScreen}
          />
        ))}
      </Menu>
    </Box>
  );
}

export { SettingsMenu };
