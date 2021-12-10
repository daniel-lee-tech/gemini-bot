import { ReactElement } from "react";
import { FormControl, FormHelperText, Input, InputLabel } from "@mui/material";
import { FormikFormControlProps } from "./types";

function FormikFormControl(props: FormikFormControlProps): ReactElement {
  const { fieldName, formik, fieldType, helperText, fieldLabel } = props;

  function hasError(): boolean {
    return formik.touched[fieldName] && Boolean(formik.errors[fieldName]);
  }

  return (
    <FormControl fullWidth variant="standard">
      <InputLabel htmlFor={fieldName}>{fieldLabel}</InputLabel>
      <Input
        fullWidth
        type={fieldType}
        name={fieldName}
        id={fieldName}
        aria-describedby={`${fieldName}-describedby`}
        value={formik.values[fieldName]}
        onChange={formik.handleChange}
        error={hasError()}
      />
      <FormHelperText error={hasError()} id={`${fieldName}-describedby`}>
        {hasError() ? formik.errors[fieldName] : helperText}
      </FormHelperText>
    </FormControl>
  );
}

export { FormikFormControl };
