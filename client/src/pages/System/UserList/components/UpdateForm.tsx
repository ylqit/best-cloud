import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {UserForm, UserVO} from "@/services/uaa/type/user";
import FormBody from "@/pages/System/UserList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getUserById, updateUser} from "@/services/uaa/user";

interface UpdateFormProps {
  updateModalVisible: boolean;
  handleUpdateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<UserVO | undefined>>
  currentRow: Partial<UserForm | UserVO | undefined>;
}

/**
 * 更新用户
 * @param fields
 */
const handleUpdate = async (fields: UserForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateUser(fields);
    hide();
    message.success('用户更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('用户更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalVisible, handleUpdateModalVisible, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑用户"
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
            const { data } = await getUserById(currentRow?.id);
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
        const success = await handleUpdate(value as UserForm);
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
