import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {PermissionForm, PermissionVO} from "@/services/uaa/type/permission";
import FormBody from "@/pages/System/PermissionList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getPermissionById, updatePermission} from "@/services/uaa/permission";

interface UpdateFormProps {
  updateModalVisible: boolean;
  handleUpdateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<PermissionVO | undefined>>
  currentRow: Partial<PermissionForm | undefined>;
}

/**
 * 更新权限
 * @param fields
 */
const handleUpdate = async (fields: PermissionForm) => {
  const hide = message.loading('更新中...');
  try {
    await updatePermission(fields);
    hide();
    message.success('权限更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('权限更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalVisible, handleUpdateModalVisible, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑权限"
      width="748px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleUpdateModalVisible(false);
          if (!updateModalVisible) {
            setCurrentRow(undefined);
          }
        }
      }}
      request={async () => {
        try {
          if (currentRow?.id) {
            const { data } = await getPermissionById(currentRow?.id);
            return data;
          } else {
            message.warn("权限id不能为空");
            handleUpdateModalVisible(false);
          }
        } catch (e) {
          message.warn("远程获取数据失败！");
          handleUpdateModalVisible(false);
        }
        return undefined;
      }}
      visible={updateModalVisible}
      onVisibleChange={handleUpdateModalVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as PermissionForm);
        if (success) {
          handleUpdateModalVisible(false);
          setCurrentRow(undefined);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <FormBody formType="update" />
    </DrawerForm>
  )
}

export default UpdateForm;
