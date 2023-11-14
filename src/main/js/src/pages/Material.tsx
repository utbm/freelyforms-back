import React from 'react';

type MaterialType = {
  id: string;
  type: string;
  location: string;
};

export default class Material extends React.Component<MaterialType> {
  fieldList: any[] = [];
  
  addField(field: any) {
    this.fieldList.push(field);
  }

  removeField(field: any) {
    const index = this.fieldList.indexOf(field);
    if (index > -1) {
      this.fieldList.splice(index, 1);
    }
  }

  updateField(oldField: any, newField: any) {
    const index = this.fieldList.indexOf(oldField);
    if (index > -1) {
      this.fieldList[index] = newField;
    }
  }
}
