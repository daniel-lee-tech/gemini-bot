import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useContext } from "react";
import { UserAxiosContext } from "../contexts/UserAxiosContext";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Response } from "../types/axios-responses/Response";
import { fetchTransfers, importTransfers } from "../axios/queries";
import { TransfersResponse } from "../types/axios-responses/TransfersResponse";
import { Box, Button, CircularProgress, Typography } from "@mui/material";

function Transfers() {
  const { userAxiosConfig } = useContext(UserAxiosContext);
  const { axiosConfig } = userAxiosConfig;

  const queryClient = useQueryClient();

  const mutation = useMutation(
    () => {
      return importTransfers(axiosConfig);
    },
    {
      onSuccess: () => {
        // Invalidate and refetch
        queryClient.invalidateQueries("transfers");
      },
    }
  );

  function handleImport() {
    mutation.mutate();
  }

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<TransfersResponse>,
    Response
  >("transfers", () => fetchTransfers(axiosConfig));

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
          width: 150,
        }));
      /* @ts-ignore */
      rows = data.entity.map((x: Transfer) => x);
    }

    return (
      <Box sx={{ height: "90vh" }}>
        <Typography sx={{ marginY: 4 }} variant="h2" gutterBottom={true}>
          Transfers
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
          : "Looks like you don't have any transfers, please import or start your first transfer in gemini!."}

        <DataGrid sx={{ height: "100%" }} rows={rows} columns={columns} />
      </Box>
    );
  }

  return <span>Error: {error?.message}</span>;
}

export {Transfers};
