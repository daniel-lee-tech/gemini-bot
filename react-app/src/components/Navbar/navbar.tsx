import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Container from "@mui/material/Container";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme } from "@mui/material";
import { SettingsMenu } from "./settings-menu";
import { MobilePagesMenu } from "./mobile-pages-menu";
import { DesktopPagesMenu } from "./desktop-pages-menu";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { selectUser, updateUser } from "../../redux/slices/userSlice";
import {
  selectAxiosConfig,
  updateAxiosConfig,
} from "../../redux/slices/axiosSlice";

interface IPage {
  route: string;
  name: string;
  onClickCallback?: () => void;
}

const Navbar = () => {
  const userInfo = useAppSelector(selectUser);
  const axiosConfig = useAppSelector(selectAxiosConfig);

  const dispatch = useAppDispatch();

  const pages = (loggedIn: boolean): IPage[] => {
    if (loggedIn) {
      return [
        // ---- API KEYS ---
        {
          route: "/protected/apikeys",
          name: "Api Keys",
        },
        // ---- TRADES ---
        {
          route: "/protected/trades",
          name: "Trades",
        },
        // ---- TRANSFERS ---
        {
          route: "/protected/transfers",
          name: "Transfers",
        },
        // ---- Analysis ---
        {
          route: "/protected/analysis",
          name: "Analysis",
        },
        // ---- LOGOUT ---
        {
          route: "/login",
          name: "Logout",
          onClickCallback: () => {
            dispatch(
              updateUser({
                id: null,
                email: null,
                jsonWebToken: null,
              })
            );

            dispatch(updateAxiosConfig({ headers: { Authorization: "" } }));
          },
        },
      ];
    } else {
      return [
        { route: "/register", name: "Register" },
        { route: "/login", name: "Login" },
      ];
    }
  };

  const settings = ["Profile", "Account", "Dashboard"];

  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(
    null
  );
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(
    null
  );

  const theme = useTheme();
  const smallerThanSmScreen = useMediaQuery(theme.breakpoints.up("sm"));

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  if (axiosConfig == null) {
    return <div>Loading...</div>;
  } else {
    return (
      <AppBar position="relative" style={{ top: 0 }}>
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <MobilePagesMenu
              handleOpenNavMenu={handleOpenNavMenu}
              anchorElNav={anchorElNav}
              handleCloseNavMenu={handleCloseNavMenu}
              pages={pages}
              userInfo={userInfo}
            />

            <DesktopPagesMenu
              pages={pages}
              userInfo={userInfo}
              handleCloseNavMenu={handleCloseNavMenu}
            />

            <SettingsMenu
              settings={settings}
              handleCloseNavMenu={handleCloseNavMenu}
              anchorElUser={anchorElUser}
              handleCloseUserMenu={handleCloseUserMenu}
              handleOpenUserMenu={handleOpenUserMenu}
              smallerThanSmScreen={smallerThanSmScreen}
            />
          </Toolbar>
        </Container>
      </AppBar>
    );
  }
};

export { Navbar };
export type { IPage };
