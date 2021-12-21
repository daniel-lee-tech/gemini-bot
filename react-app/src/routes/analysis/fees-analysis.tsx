import { Box, Typography } from "@mui/material";
import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import { useQuery } from "react-query";
import { Response } from "../../types/axios-responses/Response";
import { fetchNetFeesCurrencies } from "../../axios/queries";
import { useAppSelector } from "../../redux/hooks/redux";
import { selectAxiosConfig } from "../../redux/slices/axiosSlice";
import {
  FeesNetWorth,
  NetWorthFeesResponse,
} from "../../types/axios-responses/NetFeesCurrencyResponse";

function FeesAnalysis() {
  const axiosConfig = useAppSelector(selectAxiosConfig);

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<NetWorthFeesResponse>,
    Response
  >("netFeesCurrencies", () => fetchNetFeesCurrencies(axiosConfig));

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
          field: "netFees",
          headerName: "FeesAnalysis",
          flex: 1,
        },
      ];
      /* @ts-ignore */
      rows = data.entity.map((x: FeesNetWorth, index) => ({
        ...x,
        id: index,
      }));
    }

    return (
      <div style={{ display: "flex", height: "100%" }}>
        <div style={{ flexGrow: 1 }}>
          <Typography sx={{ marginY: 4 }} variant="h2" gutterBottom={true}>
            Fees
          </Typography>
          <Typography sx={{ marginY: 4 }} gutterBottom={true}>
            This serves as an overall accounting of all the assets you've lost
            to trading fees.
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

export { FeesAnalysis };
