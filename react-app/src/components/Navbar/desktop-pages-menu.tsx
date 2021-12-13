import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { Link } from "react-router-dom";
import { IPage } from "./navbar";
import { IUserInfo } from "../../contexts/UserAxiosContext";

interface DesktopPagesMenuProps {
  pages: (loggedIn: boolean) => IPage[];
  userInfo: IUserInfo;
  handleCloseNavMenu: () => void;
}

function DesktopPagesMenu({
  pages,
  userInfo,
  handleCloseNavMenu,
}: DesktopPagesMenuProps) {
  return (
    <>
      <Typography
        variant="h6"
        noWrap
        component="div"
        sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}
      >
        Gemini Bot
      </Typography>
      <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
        {pages(userInfo.id != null).map((page, index) => (
          <Link
            key={index}
            onClick={() => {
              handleCloseNavMenu();
              if (page.onClickCallback !== undefined) {
                page.onClickCallback();
              }
            }}
            style={{
              textDecoration: "none",
              color: "white",
              display: "block",
              paddingRight: 20,
            }}
            to={`${page.route}`}
          >
            {page.name}
          </Link>
        ))}
      </Box>
    </>
  );
}

export { DesktopPagesMenu };
