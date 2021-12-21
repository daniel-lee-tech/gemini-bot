import { Box, Typography } from "@mui/material";
import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import { useQuery } from "react-query";
import { Response } from "../../types/axios-responses/Response";
import { fetchNetTransfersCurrencies } from "../../axios/queries";
import { useAppSelector } from "../../redux/hooks/redux";
import { selectAxiosConfig } from "../../redux/slices/axiosSlice";
import {
  NetWorthTransfersResponse,
  TransfersNetWorth,
} from "../../types/axios-responses/NetTransfersCurrencyResponse";

function TransfersAnalysis() {
  const axiosConfig = useAppSelector(selectAxiosConfig);

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<NetWorthTransfersResponse>,
    Response
  >("netTransfersCurrencies", () => fetchNetTransfersCurrencies(axiosConfig));

  if (isLoading) {
    return <span>Loading...</span>;
  }

  if (isSuccess) {
    let columns: GridColDef[] = [];
    let rows = [];
    /* @ts-ignore */
    if (data.entity.length > 0) {
      /* @ts-ignore */
      columns = [
        {
          field: "currency",
          headerName: "currency",
          flex: 1,
        },
        {
          field: "netTransfers",
          headerName: "Transfers",
          flex: 1,
        },
      ];
      /* @ts-ignore */
      rows = data.entity.map((x: TransfersNetWorth, index) => ({
        ...x,
        id: index,
      }));
    }

    return (
      <div style={{ display: "flex", height: "100%" }}>
        <div style={{ flexGrow: 1 }}>
          <Typography sx={{ marginY: 4 }} variant="h2" gutterBottom={true}>
            Transfers
          </Typography>
          <Typography sx={{ marginY: 4 }} gutterBottom={true}>
            This serves as an overall accounting of all the assets you've
            transfered into and out of Gemini.
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
          />

          {rows.length === 0 &&
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

export { TransfersAnalysis };
