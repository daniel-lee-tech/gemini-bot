import { ReactElement } from "react";
import { FormControl, FormHelperText, Input, InputLabel } from "@mui/material";
import { FormikFormControlProps } from "./types";

function FormikFormControl(props: FormikFormControlProps): ReactElement {
  const { fieldName, formik, fieldType, helperText, fieldLabel } = props;

  function hasError(): boolean {
    return formik.touched[fieldName] && Boolean(formik.errors[fieldName]);
  }

  const randomId = Math.random();

  return (
    <FormControl fullWidth variant="standard">
      <InputLabel htmlFor={fieldName}>{fieldLabel}</InputLabel>
      <Input
        fullWidth
        type={fieldType}
        name={fieldName}
        id={`${fieldName}-${randomId}`}
        aria-describedby={`${fieldName}-describedby`}
        value={formik.values[fieldName]}
        onChange={formik.handleChange}
        error={hasError()}
      />
      <FormHelperText
        error={hasError()}
        id={`${fieldName}-${randomId}-describedby`}
      >
        {hasError() ? formik.errors[fieldName] : helperText}
      </FormHelperText>
    </FormControl>
  );
}

export {FormikFormControl};
