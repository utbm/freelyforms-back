# Form Data Structure Template

This document outlines the structure of the form data that the frontend will submit to the backend. It includes the form's metadata, groups, fields, and validation rules.

---

## Table of Contents

- [Form Data Object](#form-data-object)
- [Data Structure Explanation](#data-structure-explanation)
    - [Root Level](#root-level)
    - [Group Object](#group-object)
    - [Field Object](#field-object)
    - [Validation Rule Object](#validation-rule-object)
    - [Options Object (For Multiple Choice Fields)](#options-object-for-multiple-choice-fields)
- [Enum Definitions](#enum-definitions)
    - [InputType Enum Values](#inputtype-enum-values)
    - [ValidationRuleType Enum Values](#validationruletype-enum-values)
- [Sample Data for Each Field Type](#sample-data-for-each-field-type)
    - [Text Field with Validation Rules](#text-field-with-validation-rules)
    - [Text Field with Email Validation](#text-field-with-email-validation)
    - [Number Field](#number-field)
    - [Multiple Choice Field](#multiple-choice-field)
    - [Geolocation Field](#geolocation-field)
    - [Date Field](#date-field)
- [Guidelines for Backend Processing](#guidelines-for-backend-processing)
- [Considerations for Backend Development](#considerations-for-backend-development)
- [Conclusion](#conclusion)

---

## Form Data Object

```json
{
  "name": "Form Name",
  "description": "Form Description",
  "tags": ["tag1", "tag2", "tag3"],
  "groups": [
    {
      "id": "group_unique_id_1",
      "name": "Group 1",
      "fields": [
        {
          "id": "field_unique_id_1",
          "label": "First Name",
          "type": "text",
          "optional": false,
          "hidden": false,
          "validationRules": [
            {
              "type": "min_length",
              "value": "2"
            },
            {
              "type": "max_length",
              "value": "50"
            }
          ]
        },
        {
          "id": "field_unique_id_2",
          "label": "Email Address",
          "type": "text",
          "optional": false,
          "hidden": false,
          "validationRules": [
            {
              "type": "is_email"
            }
          ]
        },
        {
          "id": "field_unique_id_3",
          "label": "Age",
          "type": "number",
          "optional": true,
          "hidden": false,
          "validationRules": []
        },
        {
          "id": "field_unique_id_4",
          "label": "Favorite Colors",
          "type": "multiple_choice",
          "optional": false,
          "hidden": false,
          "options": {
            "choices": ["Red", "Green", "Blue"]
          },
          "validationRules": [
            {
              "type": "is_multiple_choice"
            }
          ]
        }
      ]
    },
    {
      "id": "group_unique_id_2",
      "name": "Group 2",
      "fields": [
        {
          "id": "field_unique_id_5",
          "label": "Location",
          "type": "geolocation",
          "optional": false,
          "hidden": false,
          "validationRules": []
        },
        {
          "id": "field_unique_id_6",
          "label": "Appointment Date",
          "type": "date",
          "optional": false,
          "hidden": false,
          "validationRules": []
        }
      ]
    }
  ]
}
```

## Data Structure Explanation

### Root Level

- **`name`**: _(string)_ The name of the form.
- **`description`**: _(string)_ A brief description of the form.
- **`tags`**: _(array of strings)_ Tags associated with the form for categorization or search purposes.
- **`groups`**: _(array of objects)_ An array containing groups, each of which can have multiple fields.

### Group Object

Each group object in the `groups` array has the following properties:

- **`id`**: _(string)_ A unique identifier for the group.
- **`name`**: _(string)_ The name of the group.
- **`fields`**: _(array of objects)_ An array containing field objects.

### Field Object

Each field object within a group's `fields` array includes:

- **`id`**: _(string)_ A unique identifier for the field.
- **`label`**: _(string)_ The label or name of the field as displayed to the user.
- **`type`**: _(string)_ The input type of the field. Possible values are:
    - `"text"`
    - `"number"`
    - `"date"`
    - `"multiple_choice"`
    - `"geolocation"`
- **`optional`**: _(boolean)_ Indicates whether the field is optional (`true`) or required (`false`).
- **`hidden`**: _(boolean)_ Indicates whether the field is hidden from the user.
- **`validationRules`**: _(array of objects)_ An array of validation rules applied to the field.
- **`options`**: _(object, optional)_ Additional options for certain input types (e.g., choices for multiple-choice fields).

### Validation Rule Object

Each validation rule object within a field's `validationRules` array includes:

- **`type`**: _(string)_ The type of validation rule. Possible values are:
    - `"is_email"`
    - `"is_radio"`
    - `"is_multiple_choice"`
    - `"regex_match"`
    - `"max_length"`
    - `"min_length"`
- **`value`**: _(string or number, optional)_ The value associated with the validation rule (e.g., the max length number, the regex pattern).

### Options Object (For Multiple Choice Fields)

- **`choices`**: _(array of strings)_ An array of choices available for multiple-choice fields.

---

## Enum Definitions

### InputType Enum Values

Possible values for the field `type` property:

- `"text"`
- `"number"`
- `"date"`
- `"multiple_choice"`
- `"geolocation"`

### ValidationRuleType Enum Values

Possible values for the validation rule `type` property:

- `"is_email"`
- `"is_radio"`
- `"is_multiple_choice"`
- `"regex_match"`
- `"max_length"`
- `"min_length"`

---

## Sample Data for Each Field Type

### Text Field with Validation Rules

```json
{
  "id": "field_unique_id_1",
  "label": "First Name",
  "type": "text",
  "optional": false,
  "hidden": false,
  "validationRules": [
    {
      "type": "min_length",
      "value": "2"
    },
    {
      "type": "max_length",
      "value": "50"
    }
  ]
}
```

### Text Field with Email Validation

```json
{
  "id": "field_unique_id_2",
  "label": "Email Address",
  "type": "text",
  "optional": false,
  "hidden": false,
  "validationRules": [
    {
      "type": "is_email"
    }
  ]
}
```

### Number Field

```json
{
  "id": "field_unique_id_3",
  "label": "Age",
  "type": "number",
  "optional": true,
  "hidden": false,
  "validationRules": []
}
```

### Multiple Choice Field

```json
{
  "id": "field_unique_id_4",
  "label": "Favorite Colors",
  "type": "multiple_choice",
  "optional": false,
  "hidden": false,
  "options": {
    "choices": ["Red", "Green", "Blue"]
  },
  "validationRules": [
    {
      "type": "is_multiple_choice"
    }
  ]
}
```

### Geolocation Field

```json
{
  "id": "field_unique_id_5",
  "label": "Location",
  "type": "geolocation",
  "optional": false,
  "hidden": false,
  "validationRules": []
}
```

### Date Field

```json
{
  "id": "field_unique_id_6",
  "label": "Appointment Date",
  "type": "date",
  "optional": false,
  "hidden": false,
  "validationRules": []
}
```

---
## Guidelines for Backend Processing

- **Unique Identifiers**: Use the `id` fields of groups and fields as primary keys or references in your database.
- **Validation Rules**: Interpret the `validationRules` array for each field to enforce data validation when users submit responses.
    - **`is_email`**: Validate that the input matches an email pattern.
    - **`max_length`** and **`min_length`**: Enforce length constraints on string inputs.
    - **`regex_match`**: Use the provided regex pattern to validate the input.
    - **`is_radio`** and **`is_multiple_choice`**: Determine whether the multiple-choice field allows single or multiple selections.
- **Optional and Hidden Fields**:
    - **`optional`**: If `false`, the backend should enforce that this field must be filled out.
    - **`hidden`**: If `true`, the field might be used for internal purposes and not displayed to the user, but the backend should still handle it appropriately.
- **Field Types**: The `type` of each field dictates how the backend should process and store the data.
    - **`text`**: Store as a string.
    - **`number`**: Store as an integer or float.
    - **`date`**: Store in a date/time format.
    - **`multiple_choice`**: Store as an array of selected choices (strings).
    - **`geolocation`**: Store as geographic coordinates (latitude and longitude).
- **Options for Multiple Choice**:
    - The `options` object contains a `choices` array, which lists all possible options for multiple-choice fields.
    - Validation should ensure that user responses match these choices.

---

## Considerations for Backend Development

- **Data Validation**: Even though the frontend may enforce some validation, it's crucial to validate data on the backend to maintain data integrity and security.
- **Extensibility**: Design your database schema to accommodate additional fields or validation rules in the future.
- **Security**:
    - Sanitize all inputs to prevent injection attacks.
    - Validate and encode data when displaying it back to users.
- **Error Handling**:
    - Provide meaningful error messages if the form data does not meet the expected structure or if validation rules are violated.
- **Versioning**:
    - If forms may change over time, consider implementing version control for form structures.