import './dnd-node.less';
import React from "react";


interface DndNodeProps {
  size?: { width: number; height: number } | undefined;
  data: any
}

export const DndNode: React.FC<DndNodeProps> = (props) => {
  const { size = { width: 126, height: 104 }, data } = props
  const { width, height } = size
  const { label, stroke, fill, fontFill, fontSize } = data

  return (
    <div
      className="container"
      style={{
        width,
        height,
        borderColor: stroke,
        backgroundColor: fill,
        color: fontFill,
        fontSize,
      }}
    >
      <span>{label}</span>
    </div>
  )
}
