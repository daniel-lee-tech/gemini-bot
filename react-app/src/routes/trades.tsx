import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import { useContext } from "react";
import { UserAxiosContext } from "../contexts/UserAxiosContext";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Response } from "../types/axios-responses/Response";
import { fetchTrades, importTrades } from "../axios/queries";
import { TransfersResponse } from "../types/axios-responses/TransfersResponse";
import { Box, Button, CircularProgress, Typography } from "@mui/material";

function Trades() {
  const { userAxiosConfig } = useContext(UserAxiosContext);
  const { axiosConfig } = userAxiosConfig;

  const queryClient = useQueryClient();

  const mutation = useMutation(
    () => {
      return importTrades(axiosConfig);
    },
    {
      onSuccess: () => {
        // Invalidate and refetch
        queryClient.invalidateQueries("trades");
      },
    }
  );

  function handleImport() {
    mutation.mutate();
  }

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<TransfersResponse>,
    Response
  >("trades", () => fetchTrades(axiosConfig));

  if (isLoading) {
    return <span>Loading...</span>;
  }

  if (isSuccess) {
    let columns: GridColDef[] = [];
    let rows = [];

    /* @ts-ignore */
    if (data.entity.length > 0) {
      /* @ts-ignore */
      columns = Object.keys(data.entity[0])
        .filter((e) => e !== "user" && e !== "advanced" && e !== "id")
        .map((e) => ({
          field: e,
          headerName: e,
          flex: 1,
          resizable: true,
        }));
      /* @ts-ignore */
      rows = data.entity.map((x: Transfer) => x);
    }

    return (
      <div style={{ display: "flex", height: "100%" }}>
        <div style={{ flexGrow: 1 }}>
          <Typography sx={{ marginY: 4 }} variant="h2" gutterBottom={true}>
            Trades
          </Typography>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              margin: 5,
              justifyContent: "flex-end",
              alignItems: "flex-end",
              fontWeight: "bold",
            }}
          >
            <Button onClick={handleImport} variant="outlined">
              {mutation.isLoading ? (
                <CircularProgress />
              ) : (
                "Import Latest Trades from Gemini"
              )}
            </Button>
          </Box>

          {rows.length === 0 && mutation.isLoading
            ? "Importing all your trades may take a long time, please be patient..."
            : rows.length === 0 &&
              "Looks like you don't have any Trades, please import or start your first Trade in gemini!."}

          <DataGrid
            autoHeight
            rows={rows}
            columns={columns}
            components={{
              Toolbar: GridToolbar,
            }}
          />
        </div>
      </div>
    );
  }

  return <span>Error: {error?.message}</span>;
}

export { Trades };
