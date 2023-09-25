import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// @mui
import { Link, Stack, IconButton, InputAdornment, TextField, Checkbox,  Container, Typography, } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import Box from '@mui/material/Box';
// components
import Iconify from '../../../components/iconify';
// ----------------------------------------------------------------------

export default function UserForm() {
  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);

  const handleClick = () => {
    navigate('/dashboard', { replace: true });
  };

  return (
    <>
      <Stack spacing={3}>
            <Typography variant="h6" gutterBottom>
              User informations
            </Typography>
        <FormLabel id="demo-row-radio-buttons-group-label">Gender</FormLabel>
        <RadioGroup row aria-labelledby="demo-row-radio-buttons-group-label" name="row-radio-buttons-group">
            <FormControlLabel value="female" control={<Radio />} label="Female" />
            <FormControlLabel value="male" control={<Radio />} label="Male" />
        </RadioGroup>
        <TextField
          
          label="First name"
          name='first_name'
          placeholder="Enter your first name"
        />
        <TextField
          label="Last name"
          name='last_name'
          placeholder="Enter your Last name"
          InputProps={{
            readOnly: false,
          }}
        />

        <TextField
          label="Age"
          name='age'
          placeholder="Enter your age"
          type='number'
        />
        <TextField
          label="Company Name"
          name='company_name'
          placeholder="Enter the name of the company"
          InputProps={{
            readOnly: false,
          }}
        />

        <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
        <InputLabel id="demo-simple-select-standard-label">Role</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          label="Role"
          name='role'
          placeholder="Choose your role"
        >
          <MenuItem value="">
            <em>Choose your role</em>
          </MenuItem>
          <MenuItem value={10}>Leader</MenuItem>
          <MenuItem value={20}>Hr Manager</MenuItem>
          <MenuItem value={30}>UI Designer</MenuItem>
          <MenuItem value={40}>Project Manager</MenuItem>
          <MenuItem value={50}>Backend Developer</MenuItem>
          <MenuItem value={60}>Full Stack Designer</MenuItem>
          <MenuItem value={70}>UI Designer</MenuItem>
          <MenuItem value={80}>UI/UX Designer</MenuItem>
        </Select>
      </FormControl>


        <FormLabel id="demo-row-radio-buttons-group-label">Verified</FormLabel>
        <RadioGroup row aria-labelledby="demo-row-radio-buttons-group-label" name="verified">
            <FormControlLabel value="is_verified" control={<Radio />} label="is verified" />
            <FormControlLabel value="not_verified" control={<Radio />} label="not verified" />
        </RadioGroup>
        <FormLabel id="demo-row-radio-buttons-group-label">Status</FormLabel>
        <RadioGroup row aria-labelledby="demo-row-radio-buttons-group-label" name="status">
            <FormControlLabel value="yes" control={<Radio />} label="Yes" />
            <FormControlLabel value="no" control={<Radio />} label="No" />
        </RadioGroup>
        <TextField
          label="Seniority"
          name='seniority'
          placeholder="Enter your seniority"
          type='number'
        />
        <TextField
          label="Entry Date"
          name='entry_date'
          type='date'
          placeholder="Enter your date of entrance"
        />
      </Stack>
<br/><br/>
      <LoadingButton  size="medium" type="submit" variant="contained" onClick={handleClick}>
        submit
      </LoadingButton>
    </>
  );
}
