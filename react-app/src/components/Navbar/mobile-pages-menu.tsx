import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { Link } from "react-router-dom";
import { IPage } from "./navbar";
import { IUserInfo } from "../../contexts/UserAxiosContext";

interface MobilePagesMenuProps {
  handleOpenNavMenu: (event: React.MouseEvent<HTMLElement>) => void;
  anchorElNav: HTMLElement | null;
  handleCloseNavMenu: () => void;
  pages: (loggedIn: boolean) => IPage[];
  userInfo: IUserInfo;
}

function MobilePagesMenu({
  handleOpenNavMenu,
  anchorElNav,
  handleCloseNavMenu,
  pages,
  userInfo,
}: MobilePagesMenuProps) {
  return (
    <>
      <Typography
        variant="h6"
        noWrap
        component="div"
        sx={{ mr: 2, display: { xs: "none", md: "flex" } }}
      >
        Gemini Bot
      </Typography>
      <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleOpenNavMenu}
          color="inherit"
        >
          <MenuIcon />
        </IconButton>
        <Menu
          id="menu-appbar"
          anchorEl={anchorElNav}
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "left",
          }}
          keepMounted
          transformOrigin={{
            vertical: "top",
            horizontal: "left",
          }}
          open={Boolean(anchorElNav)}
          onClose={handleCloseNavMenu}
          sx={{
            display: { xs: "block", md: "none" },
          }}
        >
          {pages(userInfo.id != null).map((page: IPage, index: number) => (
            <MenuItem key={index} onClick={handleCloseNavMenu}>
              <Link
                style={{
                  textDecoration: "none",
                  color: "white",
                  display: "block",
                  margin: 15,
                }}
                onClick={page?.onClickCallback}
                to={`${page.route}`}
              >
                <Typography variant="h5" textAlign="center">
                  {page.name}
                </Typography>
              </Link>
            </MenuItem>
          ))}
        </Menu>
      </Box>
    </>
  );
}

export { MobilePagesMenu };
